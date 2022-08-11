package org.vcell.cli;

import cbit.vcell.resource.NativeLib;
import cbit.vcell.resource.PropertyLoader;
import cbit.vcell.resource.ResourceUtil;
import org.sbml.libcombine.CombineArchive;

public class TestNativeLib {

    public static void main(String[] args) {
        try {
            System.out.println("starting to load combine library");
            System.setProperty("vcell.installDir", "/Users/schaff/Documents/workspace/vcell");
            PropertyLoader.loadProperties();
            ResourceUtil.setNativeLibraryDirectory();
            NativeLib.combinej.load();
            CombineArchive combineArchive = new CombineArchive();
            System.out.println("newly created combine archive "+combineArchive);
            System.out.println("completed combine library");
        }catch (Throwable e){
            e.printStackTrace();
            System.exit(1);
        }
    }
}
