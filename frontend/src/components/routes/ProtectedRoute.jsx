import { Navigate, Outlet } from "react-router-dom";
import getRoleFromToken from "../../services/utils/roleFromJwt";

const ProtectedRoute = ({ role }) => {
  const token = localStorage.getItem("token");
  const userRole = getRoleFromToken(token);

  if (!token) {
    return <Navigate to="/login" replace />;
  }

  if (role && userRole !== role) {
    return <Navigate to="/" replace />;
  }

  return <Outlet />;
};

export default ProtectedRoute;
