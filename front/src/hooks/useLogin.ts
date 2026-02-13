import { useState, useCallback } from "react";
import axios from "axios";
import { userApi } from "@/api/user.api";
import { tokenStorage } from "@/api/tokenStorage";
import type { LoginRequest, TokenResponse } from "@/types";

interface UseLoginReturn {
	login: (payload: LoginRequest) => Promise<TokenResponse>;
	loading: boolean;
	error: string | null;
}

/**
 * Hook that handles the login flow:
 * calls POST /auth/login, stores the JWT on success.
 */
export function useLogin(): UseLoginReturn {
	const [loading, setLoading] = useState(false);
	const [error, setError] = useState<string | null>(null);

	const login = useCallback(async (payload: LoginRequest): Promise<TokenResponse> => {
		setError(null);
		setLoading(true);
		try {
			const { data } = await userApi.login(payload);
			tokenStorage.set(data.token);
			return data;
		} catch (err) {
			if (axios.isAxiosError(err) && err.response?.status === 401) {
				setError("Identifiant ou mot de passe incorrect.");
			} else {
				setError("Une erreur est survenue. Veuillez réessayer.");
			}
			throw err;
		} finally {
			setLoading(false);
		}
	}, []);

	return { login, loading, error };
}
