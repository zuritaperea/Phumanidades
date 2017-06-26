package Filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Clase usada para verificar si el usuario tiene permiso para ingresara a las
 * paginas dentro de la carpeta admin
 *
 * @author AFerSor
 */
public class FilterSystem implements Filter {

    FilterConfig filterConfig = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }//fin init

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        if (session != null) {
            int iUser = (session.getAttribute("userLogged") == null ? -1 : (int) session.getAttribute("userLogged"));
             //session.setAttribute("user", 1);
            //es usuario
            //session.getAttribute("user") == null le pone el valor -1
            //int iUser = (int)session.getAttribute("user"); 
            if (iUser == 1) {
                request.setCharacterEncoding("UTF-8");
                try {
                    chain.doFilter(request, response);
                } catch (IOException iOException) {
                } catch (ServletException servletException) {
                }
            } else {
                ((HttpServletResponse) response).sendRedirect("login.xhtml");
            }
        } else {
            ((HttpServletResponse) response).sendRedirect("login.xhtml");
        }
    }//fin doFilter

    @Override
    public void destroy() {
    }

}//fin clase
