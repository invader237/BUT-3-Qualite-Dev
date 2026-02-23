import { useState, useCallback } from "react";
import type { Appearance, ThemeContextValue } from "./ThemeContext";

const STORAGE_KEY = "appearance";

function getStoredAppearance(): Appearance {
	const stored = localStorage.getItem(STORAGE_KEY);
	if (stored === "dark" || stored === "light") return stored;
	return "light";
}

/**
 * Hook to manage light/dark appearance state.
 * Persists the choice in localStorage.
 */
export function useThemeState(): ThemeContextValue {
	const [appearance, setAppearance] = useState<Appearance>(getStoredAppearance);

	const toggle = useCallback(() => {
		setAppearance((prev) => {
			const next = prev === "light" ? "dark" : "light";
			localStorage.setItem(STORAGE_KEY, next);
			return next;
		});
	}, []);

	return { appearance, toggle };
}
