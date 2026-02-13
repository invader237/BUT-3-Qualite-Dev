import { Navigate, useLocation } from "react-router-dom";
import { useAuth } from "./AuthContext";

interface RequireAuthProps {
	children: React.ReactNode;
}

/**
 * Route guard – redirects to /login when unauthenticated.
 * Saves the current location so the login page can redirect back after success.
 */
export function RequireAuth({ children }: RequireAuthProps) {
	const { status } = useAuth();
	const location = useLocation();

	if (status === "unauthenticated") {
		return <Navigate to="/login" state={{ from: location }} replace />;
	}

	// "loading" is handled by AuthProvider (nothing renders), so here status === "authenticated"
	return <>{children}</>;
}
