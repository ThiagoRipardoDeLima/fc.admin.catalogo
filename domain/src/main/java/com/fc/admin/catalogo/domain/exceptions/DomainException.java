package com.fc.admin.catalogo.domain.exceptions;

import com.fc.admin.catalogo.domain.validation.Error;

import java.util.List;

public class DomainException extends NoStacktraceException {

    private final List<Error> erros;

    protected DomainException(final String aMessage, final List<Error> anErrors) {
        super(aMessage);
        this.erros = anErrors;
    }

    public static DomainException with(final Error anErros){
        return new DomainException(anErros.message(), List.of(anErros));
    }

    public static DomainException with(final List<Error> anErros){
        return new DomainException("", anErros);
    }

    public List<Error> getErrors(){
        return erros;
    }

}
