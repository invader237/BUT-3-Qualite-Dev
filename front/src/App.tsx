import { Landmark, LayoutDashboard, Settings, CreditCard, Users } from "lucide-react"
import { Routes, Route, Navigate } from "react-router-dom"
import { Sidebar, useSidebar } from "@/components/Sidebar"
import { AppLayout } from "@/layouts/AppLayout"
import { useAuth, RequireAuth } from "@/auth"
import { MesComptesPage, AdminComptesPage, TableauDeBordPage, TransactionsPage, ParametresPage, LoginPage } from "@/pages"

function AuthenticatedApp() {
	const sidebar = useSidebar(false)
	const { user } = useAuth()

	const displayName = user ? `${user.prenom} ${user.nom}` : ""
	const isManager = user?.type === "MANAGER"

	return (
		<AppLayout
			sidebarCollapsed={sidebar.collapsed}
			sidebar={
				<Sidebar.Root sidebar={sidebar}>
					{/* ── Top: bank accounts ── */}
					<Sidebar.Header>
						{isManager
							? <Sidebar.NavItem icon={Users} label="Gestion comptes" to="/admin/comptes" />
							: <Sidebar.NavItem icon={Landmark} label="Mes comptes" to="/comptes" />
						}
					</Sidebar.Header>

					{/* ── Middle: main navigation (add items here) ── */}
					<Sidebar.Nav>
						<Sidebar.Group label="Navigation">
							<Sidebar.NavItem icon={LayoutDashboard} label="Tableau de bord" to="/dashboard" />
							<Sidebar.NavItem icon={CreditCard} label="Transactions" to="/transactions" />
							<Sidebar.NavItem icon={Settings} label="Paramètres" to="/parametres" />
						</Sidebar.Group>
					</Sidebar.Nav>

					{/* ── Bottom: user profile + collapse toggle ── */}
					<Sidebar.Footer>
						<Sidebar.Profile name={displayName} />
						<Sidebar.Toggle />
					</Sidebar.Footer>
				</Sidebar.Root>
			}
		>
			<Routes>
				<Route path="/" element={<Navigate to="/dashboard" replace />} />
				<Route path="/comptes" element={<MesComptesPage />} />
				<Route path="/admin/comptes" element={<AdminComptesPage />} />
				<Route path="/dashboard" element={<TableauDeBordPage />} />
				<Route path="/transactions" element={<TransactionsPage />} />
				<Route path="/parametres" element={<ParametresPage />} />
			</Routes>
		</AppLayout>
	)
}

function App() {
	return (
		<Routes>
			<Route path="/login" element={<LoginPage />} />
			<Route
				path="/*"
				element={
					<RequireAuth>
						<AuthenticatedApp />
					</RequireAuth>
				}
			/>
		</Routes>
	)
}

export default App
