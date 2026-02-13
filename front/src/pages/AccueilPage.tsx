import { useNavigate } from "react-router-dom";
import { Button, Flex, Text } from "@radix-ui/themes";
import { LogIn } from "lucide-react";

export function AccueilPage() {
	const navigate = useNavigate();

	function handleInfo() {
		alert("Ce TD a été donné pour les AS dans le cadre du cours de CO Avancé (Promotion 2025-2026)");
	}

	return (
		<Flex
			align="center"
			justify="center"
			direction="column"
			gap="5"
			style={{ minHeight: "100vh", background: "var(--color-background)" }}
		>
			<Text size="7" weight="bold" as="p">
				Bienvenue sur l'application ASBank
			</Text>

			<img
				src="https://dptinfo.iutmetz.univ-lorraine.fr/lp/img/Logo_IUT_Metz.Info_UL.small.png"
				alt="logo"
			/>

			<Button variant="outline" onClick={handleInfo}>
				Information
			</Button>

			<Button size="3" onClick={() => navigate("/login")}>
				<LogIn size={18} />
				Page de Login
			</Button>
		</Flex>
	);
}
