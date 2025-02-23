import type { TypeRole } from "~/types/roles";
import { APIUtils, type Session } from "~/types/utilsApi";

export const useAuth = () => {
    const userRole = useState<TypeRole | null>("userRole", () => null);

    const sessionCookie = useCookie<Session | null>("session");

    const fetchSession = async () => {
        try {
            const response = await APIUtils.getSessionInfo();
            if (response.status === 200) {
                userRole.value = response.data.role;
                sessionCookie.value = response.data;
            } else {
                userRole.value = null;
                sessionCookie.value = null;
            }
        } catch (error) {
            console.error("Erreur de récupération de session", error);
            userRole.value = null;
            sessionCookie.value = null;
        }
    };

    return { userRole, fetchSession };
};
