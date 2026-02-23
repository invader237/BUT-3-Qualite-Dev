import { createContext, useContext } from "react";

export type Appearance = "light" | "dark";

export interface ThemeContextValue {
	/** Current appearance – "light" or "dark" */
	appearance: Appearance;
	/** Toggle between light and dark */
	toggle: () => void;
}

export const ThemeContext = createContext<ThemeContextValue | null>(null);

/**
 * Hook to access theme context from any component.
 * Throws if used outside <ThemeProvider>.
 */
export function useTheme(): ThemeContextValue {
	const ctx = useContext(ThemeContext);
	if (!ctx) {
		throw new Error("useTheme must be used within a <ThemeProvider>");
	}
	return ctx;
}
