package com.blo;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.io.File;
import java.util.Arrays;

//假如bean 下有很多个（比如从第三方导入的jar包
public class MyFruits implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        //TODO
        File file=new File("com.blo.");
        String lists= Arrays.toString(file.listFiles());
        String[] strings=new String[lists.length()];
        return strings;
    }
}
