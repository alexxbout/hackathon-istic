export type TypeRole = "admin" | "rh" | "cdp" | "inconnu";

export const RoleConfig: Record<TypeRole, { label: string; icon: string; color: any }> = {
    admin: { label: "Admin", icon: "i-heroicons-shield-check", color: "teal" },
    rh: { label: "Ressources Humaines", icon: "i-heroicons-users", color: "fuchsia" },
    cdp: { label: "Chef de Projet", icon: "i-heroicons-briefcase", color: "violet" },
    inconnu: { label: "Inconnu", icon: "i-heroicons-question-mark-circle", color: "gray" }
};

export const roleOptions = Object.entries(RoleConfig).map(([value, { label, icon }]) => ({
    label,
    value: value as TypeRole,
    icon
}));