import { useCallback, useEffect, useState } from "react";
import axios from "axios";
import { tokenStorage } from "@/api/tokenStorage";
import { userApi } from "@/api/user.api";
import type { UtilisateurDto } from "@/types";
import type { AuthStatus } from "./AuthContext";

/**
 * Internal hook that manages the /me fetch lifecycle.
 * On mount, if a token exists in storage it calls GET /me.
 * Returns { status, user, refresh, logout }.
 */
export function useAuthState() {
	const [status, setStatus] = useState<AuthStatus>("loading");
	const [user, setUser] = useState<UtilisateurDto | null>(null);

	const fetchMe = useCallback(async () => {
		// No token → skip the network call
		if (!tokenStorage.get()) {
			setUser(null);
			setStatus("unauthenticated");
			return;
		}

		setStatus("loading");
		try {
			const { data } = await userApi.getMe();
			setUser(data);
			setStatus("authenticated");
		} catch (err) {
			if (axios.isAxiosError(err) && err.response?.status === 401) {
				tokenStorage.remove();
			}
			console.error("Auth check failed:", err);
			setUser(null);
			setStatus("unauthenticated");
		}
	}, []);

	const logout = useCallback(() => {
		tokenStorage.remove();
		setUser(null);
		setStatus("unauthenticated");
	}, []);

	// Check auth on mount
	useEffect(() => {
		fetchMe();
	}, [fetchMe]);

	return { status, user, refresh: fetchMe, logout };
}
