import { jwtDecode } from "jwt-decode";

export default function getRoleFromToken(token) {
  try {
    const decoded = jwtDecode(token);
    return decoded.role || null;
  } catch (error) {
    console.error("JWT Token error", error);
    return null;
  }
}