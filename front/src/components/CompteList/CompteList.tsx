import React from "react";
import { Flex, Text, Card, Badge } from "@radix-ui/themes";
import { type LucideIcon } from "lucide-react";
import { CompteListContext, useCompteListContext, type CompteListVariant } from "./CompteListContext";
import type { CompteDto } from "@/types";
import "./CompteList.css";

/* ------------------------------------------------------------------ */
/*  CompteList.Root                                                    */
/* ------------------------------------------------------------------ */

interface RootProps {
	children: React.ReactNode;
	variant?: CompteListVariant;
}

function Root({ children, variant = "user" }: RootProps) {
	return (
		<CompteListContext.Provider value={{ variant }}>
			<div className="compte-list">{children}</div>
		</CompteListContext.Provider>
	);
}

/* ------------------------------------------------------------------ */
/*  CompteList.Header – title section with icon                        */
/* ------------------------------------------------------------------ */

interface HeaderProps {
	icon: LucideIcon;
	title: string;
	description?: string;
}

function Header({ icon: Icon, title, description }: HeaderProps) {
	return (
		<div className="compte-list__header">
			<Flex align="center" gap="2">
				<Icon size={24} />
				<Text size="5" weight="bold">{title}</Text>
			</Flex>
			{description && <Text color="gray">{description}</Text>}
		</div>
	);
}

/* ------------------------------------------------------------------ */
/*  CompteList.Content – scrollable container for items                */
/* ------------------------------------------------------------------ */

interface ContentProps {
	children: React.ReactNode;
}

function Content({ children }: ContentProps) {
	return <div className="compte-list__content">{children}</div>;
}

/* ------------------------------------------------------------------ */
/*  CompteList.Group – groups items with a label (admin: per user)     */
/* ------------------------------------------------------------------ */

interface GroupProps {
	label: string;
	children: React.ReactNode;
}

function Group({ label, children }: GroupProps) {
	return (
		<div className="compte-list__group">
			<Text size="3" weight="bold" className="compte-list__group-label">{label}</Text>
			<div className="compte-list__group-items">{children}</div>
		</div>
	);
}

/* ------------------------------------------------------------------ */
/*  CompteList.Item – single account card                              */
/* ------------------------------------------------------------------ */

interface ItemProps {
	compte: CompteDto;
}

function formatCurrency(amount: number): string {
	return new Intl.NumberFormat("fr-FR", {
		style: "currency",
		currency: "EUR",
	}).format(amount);
}

function Item({ compte }: ItemProps) {
	const { variant } = useCompteListContext();

	const hasDecouvert = compte.decouvertAutorise > 0;

	return (
		<Card className="compte-list__item">
			<Flex justify="between" align="center">
				<Flex direction="column" gap="1">
					<Flex align="center" gap="2">
						<Text weight="medium">{compte.id}</Text>
						{hasDecouvert && (
							<Badge color="blue" variant="soft" size="1">Découvert</Badge>
						)}
					</Flex>
					{variant === "admin" && (
						<Text size="2" color="gray">Client : {compte.clientId}</Text>
					)}
					{hasDecouvert && (
						<Text size="1" color="gray">
							Découvert autorisé : {formatCurrency(compte.decouvertAutorise)}
						</Text>
					)}
				</Flex>
				<Text
					size="5"
					weight="bold"
					color={compte.solde < 0 ? "red" : undefined}
				>
					{formatCurrency(compte.solde)}
				</Text>
			</Flex>
		</Card>
	);
}

/* ------------------------------------------------------------------ */
/*  CompteList.Empty – placeholder when no accounts                    */
/* ------------------------------------------------------------------ */

interface EmptyProps {
	message?: string;
}

function Empty({ message = "Aucun compte trouvé." }: EmptyProps) {
	return (
		<div className="compte-list__empty">
			<Text color="gray">{message}</Text>
		</div>
	);
}

/* ------------------------------------------------------------------ */
/*  Compound export                                                    */
/* ------------------------------------------------------------------ */

export const CompteList = {
	Root,
	Header,
	Content,
	Group,
	Item,
	Empty,
};
