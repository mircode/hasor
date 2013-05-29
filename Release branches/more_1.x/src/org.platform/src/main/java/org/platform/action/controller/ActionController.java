/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.platform.action.controller;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.platform.Platform;
import org.platform.action.ActionException;
import org.platform.action.ActionInvoke;
import org.platform.action.ActionManager;
import org.platform.action.ActionNameSpace;
import org.platform.general.WebServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;
/**
 * action功能的入口。
 * @version : 2013-5-11
 * @author 赵永春 (zyc@byshell.org)
 */
@Singleton
@WebServlet("*.do")
public class ActionController extends HttpServlet {
    private static final long serialVersionUID = -2579757349905408506L;
    @Inject
    private ActionManager     actionManager    = null;
    // 
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestPath = request.getRequestURI().substring(request.getContextPath().length());
        //1.拆分请求字符串
        String actionNS = requestPath.substring(0, requestPath.lastIndexOf("/") + 1);
        String actionInvoke = requestPath.substring(requestPath.lastIndexOf("/") + 1);
        String actionMethod = actionInvoke.split("\\.")[0];
        ActionInvoke invoke = null;
        //2.获取 ActionInvoke
        try {s
            ActionNameSpace nameSpace = actionManager.getNameSpace(actionNS);
            invoke = nameSpace.getActionByName(request.getMethod(), actionMethod);
        } catch (NullPointerException e) {
            String logInfo = Platform.formatString("%s action is not defined.", actionInvoke);
            throw new ActionException(logInfo);
        }
        //3.执行调用
        try {
            Object result = invoke.invoke(request, response);
            //
        } catch (ServletException e) {
            if (e.getCause() instanceof IOException)
                throw (IOException) e.getCause();
            else
                throw e;
        }
    }
}