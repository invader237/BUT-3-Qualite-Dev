import React from "react";
import { AuthContext } from "./AuthContext";
import { useAuthState } from "./useAuthState";

interface AuthProviderProps {
	/** Rendered while /me is being fetched (blank by default to avoid flicker) */
	loading?: React.ReactNode;
	children: React.ReactNode;
}

/**
 * Wraps the application and provides auth context.
 * While checking /me, renders nothing (or the `loading` slot) to avoid flicker.
 */
export function AuthProvider({ loading = null, children }: AuthProviderProps) {
	const auth = useAuthState();

	return (
		<AuthContext.Provider value={auth}>
			{auth.status === "loading" ? loading : children}
		</AuthContext.Provider>
	);
}
