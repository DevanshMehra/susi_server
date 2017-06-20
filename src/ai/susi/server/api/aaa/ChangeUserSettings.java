package ai.susi.server.api.aaa;

import ai.susi.DAO;
import ai.susi.SusiServer;
import ai.susi.json.JsonObjectWithDefault;
import ai.susi.server.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Collection;

/**
 * Created by saurabh on 20/6/17.
 * Servlet to write user setting
 * this service accepts two parameter key and value to be stored in User settings
 * test locally at http://127.0.0.1:4000/aaa/changeUserSettings.json?key=theme&value=dark
 */
public class ChangeUserSettings extends AbstractAPIHandler implements APIHandler {

    private static final long serialVersionUID = -7418883159709458190L;

    @Override
    public String getAPIPath() {
        return "/aaa/changeUserSettings.json";
    }

    @Override
    public BaseUserRole getMinimalBaseUserRole() {
        return BaseUserRole.USER;
    }

    @Override
    public JSONObject getDefaultPermissions(BaseUserRole baseUserRole) {
        return null;
    }

    @Override
    public ServiceResponse serviceImpl(Query query, HttpServletResponse response, Authorization authorization, JsonObjectWithDefault permissions) throws APIException {
       String key = query.get("key", null);
       String value =query.get("value", null);
       if (key == null || value == null ) {
           throw new APIException(400, "Bad Service call, key or value parameters not provided");
       } else {
           if (authorization.getIdentity() == null) {
               throw new APIException(400, "Specified User Setting not found, ensure you are logged in");
           } else {
               Accounting accounting = DAO.getAccounting(authorization.getIdentity());
               JSONObject jsonObject = new JSONObject();
               jsonObject.put(key, value);
               if (accounting.getParent().has("Settings")) {
                   accounting.getParent().getJSONObject("Settings").put(key, value);
               } else {
                   accounting.getParent().put("Settings", jsonObject, authorization.getIdentity().isPersistent());
               }
               JSONObject result = new JSONObject();
               result.put("message", "You successfully changed settings to your account!");
               return new ServiceResponse(result);
           }
       }

    }
}