import { Flex, Text, Card, Switch } from "@radix-ui/themes";
import { Settings } from "lucide-react";

export function ParametresPage() {
	return (
		<Flex direction="column" gap="4">
			<Flex align="center" gap="2">
				<Settings size={24} />
				<Text size="5" weight="bold">
					Paramètres
				</Text>
			</Flex>

			<Text color="gray">Gérez les paramètres de votre application.</Text>

			<Flex direction="column" gap="3" style={{ maxWidth: 500 }}>
				<Card>
					<Flex justify="between" align="center">
						<Flex direction="column" gap="1">
							<Text weight="medium">Notifications</Text>
							<Text size="2" color="gray">Recevoir des alertes par email</Text>
						</Flex>
						<Switch defaultChecked />
					</Flex>
				</Card>

				<Card>
					<Flex justify="between" align="center">
						<Flex direction="column" gap="1">
							<Text weight="medium">Double authentification</Text>
							<Text size="2" color="gray">Sécurisez votre compte avec la 2FA</Text>
						</Flex>
						<Switch />
					</Flex>
				</Card>

				<Card>
					<Flex justify="between" align="center">
						<Flex direction="column" gap="1">
							<Text weight="medium">Mode sombre</Text>
							<Text size="2" color="gray">Modifier l'apparence de l'application</Text>
						</Flex>
						<Switch />
					</Flex>
				</Card>
			</Flex>
		</Flex>
	);
}
