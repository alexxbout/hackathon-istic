export type Role = "admin" | "rh" | "cdp";

export const RoleConfig: Record<Role, { label: string; icon: string; color: string }> = {
    admin: { label: "Admin", icon: "i-heroicons-shield-check", color: "teal" },
    rh: { label: "Ressources Humaines", icon: "i-heroicons-users", color: "fuchsia" },
    cdp: { label: "Chef de Projet", icon: "i-heroicons-briefcase", color: "violet" }
};

// Liste des rôles pour les sélecteurs
export const roleOptions = Object.entries(RoleConfig).map(([value, { label, icon }]) => ({
    label,
    value: value as Role,
    icon
}));

const getRoleConfig = (role: Role | null | undefined) => {
    return RoleConfig[role as Role] ?? { label: "Inconnu", icon: "i-heroicons-question-mark-circle", color: "gray" };
};

