import React from "react";
import "./AppLayout.css";

interface AppLayoutProps {
	sidebar: React.ReactNode;
	sidebarCollapsed?: boolean;
	children: React.ReactNode;
}

/**
 * Main application layout.
 * Renders the sidebar on the left and the page content on the right.
 */
export function AppLayout({ sidebar, sidebarCollapsed = false, children }: AppLayoutProps) {
	const rootClass = `app-layout ${sidebarCollapsed ? "app-layout--sidebar-collapsed" : ""}`;

	return (
		<div className={rootClass}>
			{sidebar}
			<main className="app-layout__content">{children}</main>
		</div>
	);
}
