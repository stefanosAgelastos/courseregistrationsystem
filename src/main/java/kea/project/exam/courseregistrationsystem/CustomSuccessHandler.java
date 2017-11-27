package kea.project.exam.courseregistrationsystem;

        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.Collection;
        import java.util.List;

        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;

        import kea.project.exam.courseregistrationsystem.model.Student;
        import kea.project.exam.courseregistrationsystem.persistence.StudentRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.core.Authentication;
        import org.springframework.security.core.GrantedAuthority;
        import org.springframework.security.web.DefaultRedirectStrategy;
        import org.springframework.security.web.RedirectStrategy;
        import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
        import org.springframework.stereotype.Component;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

/*
    @Autowired
    private StudentRepository studentRepository;
*/


    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            System.out.println("Can't redirect");
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /*
     * This method extracts the roles of currently logged-in user and returns
     * appropriate URL according to his/her role.
     */
    protected String determineTargetUrl(Authentication authentication) {
        String url = "";

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<String> roles = new ArrayList<>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        if (isStudent(roles)) {
            url = "/student";
        } else if (isTeacher(roles)) {
            url = "/teacher";
        } else {
            url = "/accessDenied";
        }

        return url;
    }

    private boolean isTeacher(List<String> roles) {
        if (roles.contains("ROLE_TEACHER")) {
            return true;
        }
        return false;
    }

    private boolean isStudent(List<String> roles) {
        if (roles.contains("ROLE_STUDENT")) {
            return true;
        }
        return false;
    }


    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

}