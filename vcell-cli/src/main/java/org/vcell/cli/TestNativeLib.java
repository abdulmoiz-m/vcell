package org.vcell.cli;

import org.sbml.libcombine.CombineArchive;

public class TestNativeLib {

    public static void main(String[] args) {
        try {
            System.out.println("starting to load combine library");
            try {
                System.loadLibrary("combinej");
            }catch (UnsatisfiedLinkError e){
                throw new RuntimeException("failed to load lib: "+e.getMessage()+" : e.g. -Djava.library.path=/Users/schaff/Documents/workspace/vcell/nativelibs/mac64", e);
            }
            // test that the library was loaded.
            CombineArchive combineArchive = new CombineArchive();
            System.out.println("newly created combine archive "+combineArchive);
            System.out.println("completed combine library");
        }catch (Throwable e){
            e.printStackTrace();
            System.exit(1);
        }
    }
}
