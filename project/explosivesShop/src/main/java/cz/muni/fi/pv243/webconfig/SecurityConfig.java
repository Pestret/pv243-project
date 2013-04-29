package cz.muni.fi.pv243.webconfig;

import org.jboss.seam.faces.event.PhaseIdType;
import org.jboss.seam.faces.security.AccessDeniedView;
import org.jboss.seam.faces.security.LoginView;
import org.jboss.seam.faces.security.RestrictAtPhase;
import org.jboss.seam.faces.view.config.ViewConfig;
import org.jboss.seam.faces.view.config.ViewPattern;
import org.jboss.seam.security.annotations.LoggedIn;

@ViewConfig
public interface SecurityConfig {

	static enum Pages1 {

		@ViewPattern("/index.xhtml")
		INDEX,

		@ViewPattern("/tables.xhtml")
		@LoginView("/login.xhtml")
		@AccessDeniedView("/denied.xhtml")
		@LoggedIn
		@Admin
		@RestrictAtPhase({ PhaseIdType.RESTORE_VIEW,
				PhaseIdType.INVOKE_APPLICATION })
		TABLES,

		@ViewPattern("/admin_page.xhtml")
		@LoginView("/login.xhtml")
		@AccessDeniedView("/denied.xhtml")
		@LoggedIn
		@Admin
		@RestrictAtPhase({ PhaseIdType.RESTORE_VIEW,
				PhaseIdType.INVOKE_APPLICATION })
		ADMIN_PAGE,

		@ViewPattern("/seller_page.xhtml")
		@LoginView("/login.xhtml")
		@AccessDeniedView("/denied.xhtml")
		@LoggedIn
//		@Seller
		@RestrictAtPhase({ PhaseIdType.RESTORE_VIEW,
				PhaseIdType.INVOKE_APPLICATION })
		SELLER_PAGE,

		@ViewPattern("/orders.xhtml")
		@LoginView("/login.xhtml")
		@AccessDeniedView("/denied.xhtml")
		@LoggedIn
		@RestrictAtPhase({ PhaseIdType.RESTORE_VIEW,
				PhaseIdType.INVOKE_APPLICATION })
		TEST,

		/*
		 * @ViewPattern("/view.xhtml")
		 * 
		 * @UrlMapping(pattern="/view/#{id}/") VIEW,
		 * 
		 * @ViewPattern("/edit.xhtml")
		 * 
		 * @LoginView("/login.xhtml")
		 * 
		 * @AccessDeniedView("/denied.xhtml")
		 * 
		 * @LoggedIn
		 * 
		 * @IsManagerOf
		 * 
		 * @RestrictAtPhase({PhaseIdType.RESTORE_VIEW,
		 * PhaseIdType.INVOKE_APPLICATION}) EDIT,
		 * 
		 * @ViewPattern("/bakerylist.xhtml") BAKERYLIST,
		 * 
		 * @ViewPattern("/managers.xhtml")
		 * 
		 * @LoginView("/login.xhtml")
		 * 
		 * @AccessDeniedView("/denied.xhtml")
		 * 
		 * @LoggedIn
		 * 
		 * @Admin
		 * 
		 * @RestrictAtPhase({PhaseIdType.RESTORE_VIEW,
		 * PhaseIdType.INVOKE_APPLICATION}) MANAGERS
		 */
	}
}
