package com.fc.admin.catalogo.domain.exceptions;

import com.fc.admin.catalogo.domain.validation.Error;

import java.util.List;

public class DomainException extends RuntimeException {

    private final List<Error> erros;

    protected DomainException(final List<Error> anErrors) {
        super("", null, true, false);
        this.erros = anErrors;
    }

    public static DomainException with(final List<Error> anErros){
        return new DomainException(anErros);
    }

    public List<Error> getErrors(){
        return erros;
    }

}
