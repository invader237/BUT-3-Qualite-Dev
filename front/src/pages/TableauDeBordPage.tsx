import { Flex, Text, Card } from "@radix-ui/themes";
import { LayoutDashboard } from "lucide-react";

export function TableauDeBordPage() {
	return (
		<Flex direction="column" gap="4">
			<Flex align="center" gap="2">
				<LayoutDashboard size={24} />
				<Text size="5" weight="bold">
					Tableau de bord
				</Text>
			</Flex>

			<Text color="gray">Vue d'ensemble de votre activité financière.</Text>

			<Flex gap="3" wrap="wrap">
				<Card style={{ flex: "1 1 200px" }}>
					<Flex direction="column" gap="1">
						<Text size="2" color="gray">Solde total</Text>
						<Text size="6" weight="bold">9 565,80 €</Text>
					</Flex>
				</Card>

				<Card style={{ flex: "1 1 200px" }}>
					<Flex direction="column" gap="1">
						<Text size="2" color="gray">Revenus ce mois</Text>
						<Text size="6" weight="bold" color="green">+2 150,00 €</Text>
					</Flex>
				</Card>

				<Card style={{ flex: "1 1 200px" }}>
					<Flex direction="column" gap="1">
						<Text size="2" color="gray">Dépenses ce mois</Text>
						<Text size="6" weight="bold" color="red">-1 340,20 €</Text>
					</Flex>
				</Card>
			</Flex>
		</Flex>
	);
}
