package co.spring.rest.error;

public class CreatedError extends Error{

    public CreatedError(){super();}

    public CreatedError(String info, String description, Throwable throwable){
        super(info, description, throwable);
    }

}
