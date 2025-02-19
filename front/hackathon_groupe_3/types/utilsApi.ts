import axios from "axios";
import type { Competence, Pool, Profil, Projet, Reservation } from "./entities";

export class APIUtils {
    private static baseUrl = "/api/v1";

    static async getProfils() {
        return axios.get(`${this.baseUrl}/profils`);
    }

    static async getProfil(id: number) {
        return axios.get(`${this.baseUrl}/profils/${id}`);
    }

    static async addProfil(profil: Profil) {
        return axios.post(`${this.baseUrl}/profils`, profil);
    }

    static async updateProfil(id: number, profil: Partial<Profil>) {
        return axios.put(`${this.baseUrl}/profils/${id}`, profil);
    }

    static async deleteProfil(id: number) {
        return axios.delete(`${this.baseUrl}/profils/${id}`);
    }

    static async searchProfil(params: any) {
        return axios.get(`${this.baseUrl}/profils/search`, { params });
    }

    static async getProjets() {
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

    static async getCompetences() {
        return axios.get(`${this.baseUrl}/competences`);
    }

    static async addCompetence(competence: Competence) {
        return axios.post(`${this.baseUrl}/competences`, competence);
    }

    static async deleteCompetence(id: number) {
        return axios.delete(`${this.baseUrl}/competences/${id}`);
    }
}
