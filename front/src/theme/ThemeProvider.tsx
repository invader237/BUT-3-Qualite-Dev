import React from "react";
import { Theme } from "@radix-ui/themes";
import { ThemeContext } from "./ThemeContext";
import { useThemeState } from "./useThemeState";

interface ThemeProviderProps {
	children: React.ReactNode;
}

/**
 * Wraps the application and provides theme context.
 * Delegates to Radix UI <Theme> with the current appearance.
 */
export function ThemeProvider({ children }: ThemeProviderProps) {
	const theme = useThemeState();

	return (
		<ThemeContext.Provider value={theme}>
			<Theme accentColor="blue" radius="medium" appearance={theme.appearance}>
				{children}
			</Theme>
		</ThemeContext.Provider>
	);
}
