package co.spring.rest.error;

public class NotFoundError extends Error{

    public NotFoundError(){super();}

    public NotFoundError(String info, String description, Throwable throwable){
        super(info, description, throwable);
    }

}
