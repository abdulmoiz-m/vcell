package org.vcell.cli.vcml;

import cbit.util.xml.VCLogger;
import cbit.vcell.resource.PropertyLoader;

import org.vcell.cli.CLIPythonManager;
import org.vcell.cli.CLIUtils;
import org.vcell.util.DataAccessException;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.Callable;

@Command(name = "convert", description = "convert from VCML to COMBINE archive (.omex)")
public class ConvertCommand implements Callable<Integer> {
    @Option(names = { "-m", "--outputModelFormat" }, defaultValue = "SBML", description = "expecting SBML or VCML")
    private ModelFormat outputModelFormat = ModelFormat.SBML;

    @Option(names = { "-i", "--inputFilePath" })
    private File inputFilePath;

    @Option(names = { "-o", "--outputFilePath" })
    private File outputFilePath;

    @Option(names = "--hasDataOnly")
    boolean bHasDataOnly;

    @Option(names = "--makeLogsOnly")
    boolean bMakeLogsOnly;

    @Option(names = "--nonSpatialOnly")
    boolean bNonSpatialOnly;

    @Option(names = "--forceLogFiles")
    boolean bForceLogFiles;

    @Option(names = "--validate")
    boolean bValidateOmex;

    @Option(names = {"-h", "--help"}, description = "show this help message and exit", usageHelp = true)
    private boolean help;

    public Integer call() {
        try {
            PropertyLoader.loadProperties();
            VCLogger vcLogger = new CLIUtils.LocalLogger();

            try (CLIDatabaseService cliDatabaseService = new CLIDatabaseService()) {
            	try {
	                CLIPythonManager.getInstance().instantiatePythonProcess();
	                VcmlOmexConverter.convertFiles(cliDatabaseService, inputFilePath, outputFilePath,
	                        outputModelFormat, bHasDataOnly, bMakeLogsOnly, bNonSpatialOnly, bForceLogFiles, bValidateOmex,
	                        vcLogger);
            	}finally {
            		try {
            			CLIPythonManager.getInstance().closePythonProcess();
            		}catch (Exception e) {
            			
            		}
            	}
            } catch (IOException | SQLException | DataAccessException e) {
                e.printStackTrace(System.err);
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
