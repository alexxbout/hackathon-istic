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
    competences: Competence[];
    cvUrl?: string;
    profile_picture: string;
    ville: string;
}

export interface UpdateProfil {
    nom: string;
    prenom: string;
    experience: number;
    competenceIds: number[];
    ville: string;
}

export interface Projet {
    id?: number;
    nom: string;
    dateDebut: Date;
    dateFin: Date;
    reservations: Reservation[];
}

export interface Pool {
    id?: number;
    user_id: number;
    nom: string;
    experience: number;
    competences: Competence[];
}

export interface User {
    id?: number;
    mail: string;
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
    projetId: number;
    profil: Profil;
    dateDebut: Date;
    dateFin: Date;
}
