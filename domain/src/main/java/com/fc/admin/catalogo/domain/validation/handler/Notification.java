package com.fc.admin.catalogo.domain.validation.handler;

import com.fc.admin.catalogo.domain.exceptions.DomainException;
import com.fc.admin.catalogo.domain.validation.Error;
import com.fc.admin.catalogo.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {

    //lista de erros
    private final List<Error> errors;

    private Notification(final List<Error> errors){
        this.errors = errors;
    }

    /*
    /factore method para passar os errors cuja retorno e uma estancia com uma lista vazia
    */
    public static Notification create(){
        return new Notification(new ArrayList<>());
    }

    public static Notification create(Error anError){
        return new Notification(new ArrayList<>()).append(anError);
    }

    public static Notification create(Throwable t){
        return create(new Error(t.getMessage()));
    }

    @Override
    public Notification append(final Error anError) {
        this.errors.add(anError);
        return this;
    }

    @Override
    public Notification append(final ValidationHandler anHandler) {
        this.errors.addAll(anHandler.getErrors());
        return this;
    }

    @Override
    public Notification validate(Validation aValidation) {
        try {
            aValidation.validate();
        } catch (DomainException e) {
            this.errors.addAll(e.getErrors());
        } catch (Throwable t){
            this.errors.add(new Error(t.getMessage()));
        }

        return this;
    }

    @Override
    public List<Error> getErrors() {
        return this.errors;
    }
}
