import { createContext, useContext } from "react";
import type { UtilisateurDto } from "@/types";

export type AuthStatus = "loading" | "authenticated" | "unauthenticated";

export interface AuthContextValue {
	/** Current auth status – "loading" means the /me call hasn't resolved yet */
	status: AuthStatus;
	/** The authenticated user, or null */
	user: UtilisateurDto | null;
	/** Re-fetch /me (useful after login) */
	refresh: () => Promise<void>;
	/** Clear local state (useful after logout) */
	logout: () => void;
}

export const AuthContext = createContext<AuthContextValue | null>(null);

/**
 * Hook to access auth context from any component.
 * Throws if used outside <AuthProvider>.
 */
export function useAuth(): AuthContextValue {
	const ctx = useContext(AuthContext);
	if (!ctx) {
		throw new Error("useAuth must be used within an <AuthProvider>");
	}
	return ctx;
}
