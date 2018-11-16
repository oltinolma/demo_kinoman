package uz.oltinolma.producer.common;

import org.springframework.stereotype.Component;
import uz.oltinolma.producer.security.model.exceptionModels.BaseResponse;
@Component
public class BaseResponses {

    public BaseResponse duplicateKeyErrorResponse(String name) {
        BaseResponse errorResponse = new BaseResponse();
        errorResponse.setMessage("Unique error : name {" + name + "}");
        errorResponse.setStatus(500);
        return errorResponse;
    }

    public BaseResponse serverErrorResponse() {
        BaseResponse errorResponse = new BaseResponse();
        errorResponse.setStatus(500);
        errorResponse.setMessage("Server error!");
        return errorResponse;
    }

    public BaseResponse serverErrorResponse(int status, String message) {
        BaseResponse errorResponse = new BaseResponse();
        errorResponse.setStatus(status);
        errorResponse.setMessage(message);
        return errorResponse;
    }

    public BaseResponse successMessage() {
        return new BaseResponse();
    }
}
