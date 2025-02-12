import axios from 'axios';
import authHeader from './utils/authHeader';

const API_URL = 'http://localhost:8080/api/v1/admin';

class UserService {

  async getAdminInfo() {
    try {
      const response = await axios.get(API_URL, { headers: authHeader() });
 
      return response.data;
    } catch (error) {
      console.error("Error fetching admin info:");
    }
  }

}

export default new UserService();