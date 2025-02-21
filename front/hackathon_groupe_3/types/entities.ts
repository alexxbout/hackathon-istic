import type { TypeRole } from "./roles";

export interface SiteAddr {
    id?: number;
    ville: string;
}

export interface Profil {
    id?: number;
    nom: string;
    prenom: string;
    experience: number;
    competences: Competence[],
    cvUrl?: string;
    profile_picture: string;
    ville: string;
}

export interface ProfilCompetences {
    profil: Profil;
    competences: Competence[];
}

export interface Projet {
    id?: number;
    nom: string;
    date_debut: Date;
    date_fin: Date;
}

export interface Pool {
    id?: number;
    user_id: number;
    nom: string;
    experience: number;
}

export interface User {
    id?: number;
    email: string;
    role: TypeRole;
    nom: string;
    prenom: string;
}

export interface Role {
    id?: number;
    nom: TypeRole;
}

export interface Competence {
    id?: number;
    nom: string;
}

export interface MatchProfilCompetence {
    profil_id: number;
    competence_id: number;
}

export interface MatchPoolCompetence {
    pool_id: number;
    competence_id: number;
}

export interface Reservation {
    id?: number;
    profil_id: number;
    date_debut: Date;
    date_fin: Date;
    id_projet: number;
}
