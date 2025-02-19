export interface SiteAddr {
    id: number;
    ville: string;
}

export interface Profil {
    id: number;
    nom: string;
    prenom: string;
    experience: number;
    site_id: number;
    cv_url: string;
    profile_picture: string;
}

export interface Projet {
    id: number;
    nom: string;
    date_debut: Date;
    date_fin: Date;
}

export interface Pool {
    id: number;
    user_id: number;
    nom: string;
    experience: number;
}

export interface User {
    id: number;
    role_id: number;
    nom: string;
}

export interface Role {
    id: number;
    nom: string;
}

export interface Competence {
    id: number;
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
    id: number;
    profil_id: number;
    date_debut: Date;
    date_fin: Date;
    id_projet: number;
}
