package co.spring.rest.error;

public class Error extends RuntimeException{

    private String info;

    private String description;

    private Throwable throwable;

    public Error(){}

    public Error(String info, String description, Throwable throwable){
        super(info, throwable);
        this.info = info;
        this.description = description;
        this.throwable = throwable;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
