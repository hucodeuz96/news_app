package hucode.news_app.exception;

//@ResponseStatus
public class ResourceNotFoundException extends RuntimeException {
    //programmani to'xtatmaydi
    private String resourceName;
    private String fieldName;
    private Object fieldValue ;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public ResourceNotFoundException(String authentification, String password) {
        super(String.format("%s not found with %s ", authentification, password));
        this.resourceName = authentification;
        this.fieldName = password;
    }


    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
