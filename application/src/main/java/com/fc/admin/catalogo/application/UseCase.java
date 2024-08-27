package com.fc.admin.catalogo.application;

import com.fc.admin.catalogo.domain.Category;

public class UseCase {
    public Category execute(){
        return new Category();
    }
}