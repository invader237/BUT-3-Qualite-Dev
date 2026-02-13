import React from "react";
import { NavLink } from "react-router-dom";
import { Tooltip, Avatar } from "@radix-ui/themes";
import { PanelLeftClose, PanelLeftOpen, type LucideIcon } from "lucide-react";
import { SidebarContext, useSidebarContext } from "./SidebarContext";
import type { UseSidebarReturn } from "./useSidebar";
import "./Sidebar.css";

/* ------------------------------------------------------------------ */
/*  Sidebar.Root                                                       */
/* ------------------------------------------------------------------ */

interface RootProps {
	children: React.ReactNode;
	sidebar: UseSidebarReturn;
}

function Root({ children, sidebar }: RootProps) {
	return (
		<SidebarContext.Provider value={sidebar}>
			<aside className={`sidebar ${sidebar.collapsed ? "sidebar--collapsed" : ""}`}>
				{children}
			</aside>
		</SidebarContext.Provider>
	);
}

/* ------------------------------------------------------------------ */
/*  Sidebar.Header – top section of the sidebar                        */
/* ------------------------------------------------------------------ */

interface HeaderProps {
	children: React.ReactNode;
}

function Header({ children }: HeaderProps) {
	return <div className="sidebar__header">{children}</div>;
}

/* ------------------------------------------------------------------ */
/*  Sidebar.Nav – scrollable middle section                            */
/* ------------------------------------------------------------------ */

interface NavProps {
	children: React.ReactNode;
}

function Nav({ children }: NavProps) {
	return <nav className="sidebar__nav">{children}</nav>;
}

/* ------------------------------------------------------------------ */
/*  Sidebar.Group – optional grouping of NavItems with a label         */
/* ------------------------------------------------------------------ */

interface GroupProps {
	label?: string;
	children: React.ReactNode;
}

function Group({ label, children }: GroupProps) {
	const { collapsed } = useSidebarContext();
	return (
		<div className="sidebar__group">
			{label && !collapsed && (
				<span className="sidebar__group-label">{label}</span>
			)}
			{children}
		</div>
	);
}

/* ------------------------------------------------------------------ */
/*  Sidebar.NavItem – single navigation button                         */
/* ------------------------------------------------------------------ */

interface NavItemProps {
	icon: LucideIcon;
	label: string;
	to?: string;
	active?: boolean;
	onClick?: () => void;
}

function NavItem({ icon: Icon, label, to, active, onClick }: NavItemProps) {
	const { collapsed } = useSidebarContext();

	// When `to` is provided, render a NavLink for client-side routing
	if (to) {
		const link = (
			<NavLink
				to={to}
				className={({ isActive }) =>
					`sidebar__nav-item ${isActive ? "sidebar__nav-item--active" : ""}`
				}
				aria-label={label}
				onClick={onClick}
			>
				<span className="sidebar__nav-icon">
					<Icon size={20} strokeWidth={1.8} />
				</span>
				<span className="sidebar__nav-item-label" aria-hidden={collapsed}>{label}</span>
			</NavLink>
		);

		return link;
	}

	// Fallback: plain button (no routing)
	const button = (
		<button
			className={`sidebar__nav-item ${active ? "sidebar__nav-item--active" : ""}`}
			onClick={onClick}
			aria-label={label}
		>
			<span className="sidebar__nav-icon">
				<Icon size={20} strokeWidth={1.8} />
			</span>
			<span className="sidebar__nav-item-label" aria-hidden={collapsed}>{label}</span>
		</button>
	);

	if (collapsed) {
		return (
			<Tooltip content={label} side="right">
				{button}
			</Tooltip>
		);
	}

	return button;
}

/* ------------------------------------------------------------------ */
/*  Sidebar.Footer – bottom section (sticky)                           */
/* ------------------------------------------------------------------ */

interface FooterProps {
	children: React.ReactNode;
}

function Footer({ children }: FooterProps) {
	return <div className="sidebar__footer">{children}</div>;
}

/* ------------------------------------------------------------------ */
/*  Sidebar.Profile – user profile display                             */
/* ------------------------------------------------------------------ */

interface ProfileProps {
	name: string;
	avatarUrl?: string;
	fallback?: string;
	onClick?: () => void;
}

function Profile({ name, avatarUrl, fallback, onClick }: ProfileProps) {
	const { collapsed } = useSidebarContext();

	const initials =
		fallback ??
		name
			.split(" ")
			.map((w) => w[0])
			.join("")
			.toUpperCase()
			.slice(0, 2);

	const content = (
		<button className="sidebar__profile" onClick={onClick} aria-label={`Profil de ${name}`}>
			<span className="sidebar__nav-icon">
				<Avatar size="2" radius="full" src={avatarUrl} fallback={initials} />
			</span>
			<span className="sidebar__profile-name" aria-hidden={collapsed}>{name}</span>
		</button>
	);

	if (collapsed) {
		return (
			<Tooltip content={name} side="right">
				{content}
			</Tooltip>
		);
	}

	return content;
}

/* ------------------------------------------------------------------ */
/*  Sidebar.Toggle – collapse/expand button                            */
/* ------------------------------------------------------------------ */

function Toggle() {
	const { collapsed, toggle } = useSidebarContext();

	const Icon = collapsed ? PanelLeftOpen : PanelLeftClose;
	const label = collapsed ? "Ouvrir le menu" : "Réduire le menu";

	const button = (
		<button className="sidebar__toggle" onClick={toggle} aria-label={label}>
			<span className="sidebar__nav-icon">
				<Icon size={20} strokeWidth={1.8} />
			</span>
		</button>
	);

	if (collapsed) {
		return (
			<Tooltip content={label} side="right">
				{button}
			</Tooltip>
		);
	}

	return button;
}

/* ------------------------------------------------------------------ */
/*  Compound export                                                    */
/* ------------------------------------------------------------------ */

export const Sidebar = {
	Root,
	Header,
	Nav,
	Group,
	NavItem,
	Footer,
	Profile,
	Toggle,
};
