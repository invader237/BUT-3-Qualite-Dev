import axios from "axios";
import { tokenStorage } from "./tokenStorage";

/**
 * Global Axios instance – single source of truth for base config + interceptors.
 * Every API entity file imports this instance.
 */
export const axiosInstance = axios.create({
	baseURL: import.meta.env.VITE_API_BASE_URL ?? "http://localhost:8080",
	headers: {
		Accept: "application/json",
		"Content-Type": "application/json",
	},
});

/* ── Request interceptor: attach JWT token ── */
axiosInstance.interceptors.request.use((config) => {
	const token = tokenStorage.get();
	if (token) {
		config.headers.Authorization = `Bearer ${token}`;
	}
	return config;
});

/* ── Response interceptor: clear token on 401 ── */
axiosInstance.interceptors.response.use(
	(response) => response,
	(error) => {
		if (axios.isAxiosError(error) && error.response?.status === 401) {
			tokenStorage.remove();
		}
		return Promise.reject(error);
	},
);
