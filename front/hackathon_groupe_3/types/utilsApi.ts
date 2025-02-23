import type { AxiosResponse } from "axios";
import axios from "axios";
import type { Competence, Pool, Profil, Projet, Reservation, UpdateProfil, User } from "./entities";
import type { TypeRole } from "./roles";

interface Login {
    message: string;
    role: string;
    nom: string;
    prenom: string;
    mail: string;
    image: string;
    firstLogin: true;
}

interface UserSession {
    nom: string;
    prenom: string;
    email: string;
    password: string;
    role: string;
    image: string;
}

export interface Session {
    nom: string;
    prenom: string;
    mail: string;
    image: string;
    firstLogin: boolean;
    role: TypeRole;
}

export class APIUtils {
    private static baseUrl = "/api/v1";

    static async login(mail: string, password: string): Promise<AxiosResponse<Login>> {
        return axios.post(`${this.baseUrl}/users/login`, { mail, password });
    }

    static async logout(): Promise<void> {
        return axios.post(`${this.baseUrl}/users/logout`);
    }

    static async getSessionInfo(): Promise<AxiosResponse<Session>> {
        return axios.get(`${this.baseUrl}/users/getSessionInfo`);
    }

    static async getProfils() {
        return axios.get(`${this.baseUrl}/profils`);
    }

    static async getProfil(id: number) {
        return axios.get(`${this.baseUrl}/profils/${id}`);
    }

    static async addProfil(profil: Profil) {
        return axios.post(`${this.baseUrl}/profils`, profil);
    }

    static async updateProfil(id: number, profil: UpdateProfil) {
        return axios.put(`${this.baseUrl}/profils/${id}`, profil);
    }

    static async deleteProfil(id: number) {
        return axios.delete(`${this.baseUrl}/profils/${id}`);
    }

    static async searchProfil(params: any) {
        return axios.get(`${this.baseUrl}/profils/search`, { params });
    }
    
    static async uploadCv(id: number, file: string) {
        return axios.post(`${this.baseUrl}/profils/${id}/cv`, { file: file });
    }

    static async getProjets(): Promise<AxiosResponse<Projet[]>> {
        return axios.get(`${this.baseUrl}/projets`);
    }

    static async addProjet(projet: Projet) {
        return axios.post(`${this.baseUrl}/projets`, projet);
    }

    static async getPools() {
        return axios.get(`${this.baseUrl}/pools`);
    }

    static async addPool(pool: Pool) {
        return axios.post(`${this.baseUrl}/pools`, pool);
    }

    static async getPool(id: number) {
        return axios.get(`${this.baseUrl}/pools/${id}`);
    }

    static async updatePool(id: number, pool: Partial<Pool>) {
        return axios.put(`${this.baseUrl}/pools/${id}`, pool);
    }

    static async deletePool(id: number) {
        return axios.delete(`${this.baseUrl}/pools/${id}`);
    }

    static async getReservations() {
        return axios.get(`${this.baseUrl}/reservations`);
    }

    static async addReservation(reservation: Reservation) {
        return axios.post(`${this.baseUrl}/reservations`, reservation);
    }

    static async getReservation(id: number) {
        return axios.get(`${this.baseUrl}/reservations/${id}`);
    }

    static async deleteReservation(id: number) {
        return axios.delete(`${this.baseUrl}/reservations/${id}`);
    }

    static async getCompetences(): Promise<AxiosResponse<Competence[]>> {
        return axios.get(`${this.baseUrl}/competences`);
    }

    static async addCompetence(competence: Competence) {
        return axios.post(`${this.baseUrl}/competences`, competence);
    }

    static async deleteCompetence(id: number) {
        return axios.delete(`${this.baseUrl}/competences/${id}`);
    }

    static async getUsers(): Promise<AxiosResponse<User[]>> {
        return axios.get(`${this.baseUrl}/users/all`);
    }

    static async addUser(user: User) {
        return axios.post(`${this.baseUrl}/users`, user);
    }

    static async deleteUser(id: number) {
        return axios.delete(`${this.baseUrl}/users/${id}`);
    }
}
