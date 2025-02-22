import type { TypeRole } from "~/types/roles";
import { APIUtils } from "~/types/utilsApi";

export const useAuth = () => {
    const userRole = useState<TypeRole | null>("userRole", () => null);

    const roleCookie = useCookie<TypeRole>("role");

    const fetchSession = async () => {
        try {
            const response = await APIUtils.getSessionInfo();
            if (response.status === 200) {
                userRole.value = response.data.role;
                roleCookie.value = response.data.role;
            } else {
                userRole.value = null;
                roleCookie.value = "inconnu";
            }
        } catch (error) {
            console.error("Erreur de récupération de session", error);
            userRole.value = null;
            roleCookie.value = "inconnu";
        }
    };

    return { userRole, fetchSession };
};
