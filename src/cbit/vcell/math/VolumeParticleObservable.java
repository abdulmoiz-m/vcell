package cbit.vcell.math;

import java.beans.PropertyVetoException;

import org.vcell.util.CommentStringTokenizer;
import org.vcell.util.Matchable;

import cbit.vcell.math.Variable.Domain;

@SuppressWarnings("serial")
public class VolumeParticleObservable extends ParticleObservable {

	public VolumeParticleObservable(String name, Domain domain, ObservableType t) {
		super(name, domain, t);
	}
	public VolumeParticleObservable(String name, Domain domain) {
		super(name, domain);
	}

//	@Override
//	public String getVCML() throws MathException {
//		String vcml = VCML.VolumeParticleObservable+"    "+getQualifiedName() + "   ";
//		boolean bFirst = true;
//		for (ParticleSpeciesPattern pattern : getParticleSpeciesPatterns()) {
//			if (bFirst){
//				vcml += " " + pattern.getName();
//			}else{
//				vcml += ", " + pattern.getName();
//			}
//		}
//		return vcml + "\n";
//	}

	@Override
	public boolean compareEqual(Matchable object, boolean bIgnoreMissingDomains) {
		if (object instanceof VolumeParticleObservable){
			if (!compareEqual0(object, bIgnoreMissingDomains)){
				return false;
			}
			return true;
		}
		return false;
	}

	public void read(MathDescription mathDescription, CommentStringTokenizer tokens, VariableHash varHash) throws MathFormatException {
		String token = null;
		token = tokens.nextToken();
		if (!token.equalsIgnoreCase(VCML.BeginBlock)){
			throw new MathFormatException("unexpected token " + token + " expecting " + VCML.BeginBlock);
		}			
		while (tokens.hasMoreTokens()){
			token = tokens.nextToken();
			if (token.equalsIgnoreCase(VCML.EndBlock)){
				break;
			}			
			if (token.equalsIgnoreCase(VCML.ParticleMolecularType)){
				token = tokens.nextToken();
				String name = token;
				try {
					setType(ObservableType.fromString(name));
				} catch (PropertyVetoException e) {
					e.printStackTrace();
				}
				continue;
			} else if(token.equalsIgnoreCase(VCML.VolumeParticleSpeciesPatterns)){
				token = tokens.nextToken();
				if (!token.equalsIgnoreCase(VCML.BeginBlock)){
					throw new MathFormatException("unexpected token " + token + " expecting " + VCML.BeginBlock);
				}			
				while (tokens.hasMoreTokens()){
					token = tokens.nextToken();
					if (token.equalsIgnoreCase(VCML.EndBlock)){
						break;
					}
					Domain domain = Variable.getDomainFromCombinedIdentifier(token);
					String name = Variable.getNameFromCombinedIdentifier(token);
					ParticleSpeciesPattern psp = (ParticleSpeciesPattern)(varHash.getVariable(name));
					if(psp != null) {		// already exists
						addParticleSpeciesPattern(psp);
					} else {
						throw new MathFormatException("failed to find "+VCML.VolumeParticleSpeciesPattern + " named " + name);
//						VolumeParticleSpeciesPattern vpsp = new VolumeParticleSpeciesPattern(name, domain); // just a stub
//						addParticleSpeciesPattern(vpsp);
					}
					continue;
				}	
			}
//			throw new MathFormatException("unexpected identifier "+token);
		}
	}	
}
