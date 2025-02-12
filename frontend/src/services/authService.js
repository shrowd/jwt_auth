import axios from "axios";
import getRoleFromToken from "./utils/roleFromJwt"

const API_URL = "http://localhost:8080/api/v1/auth/";

class AuthService {
  async login(email, password) {
    try {
      const response = await axios.post(API_URL + "signin", { email, password });
      if (response.data.token) {
        localStorage.setItem("token", response.data.token);
      }
      const role = getRoleFromToken(response.data.token);

      return role;
    } catch (error) {
      throw new Error("Login failed");
    }
  }

  async register(firstName, lastName, email, password) {
    try {
      await axios.post(API_URL + "signup", { firstName, lastName, email, password });
    } catch (error) {
      throw new Error("Registration failed");
    }
  }

  logout() {
    localStorage.removeItem("token");
  }

}

export default new AuthService();
