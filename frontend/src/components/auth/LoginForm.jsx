import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import ClipLoader from "react-spinners/ClipLoader";

import AuthService from "../../services/authService";


const LoginForm = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setLoading(true);

    try {
      const role = await AuthService.login(email, password);
     
      if (role === "ADMIN") {
        navigate("/admin");
      } else {
        navigate("/user");
      }
    } catch (err) {
      setError("Invalid credentials");
    }
    finally{
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-900 flex items-center justify-center">
      <div className="bg-gray-800 p-8 rounded-lg shadow-lg w-full max-w-md">
        <h2 className="text-2xl text-white font-bold mb-6 text-center">
          Sign in
        </h2>
        {error && <div className="text-red-500 text-center mb-4">{error}</div>}
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label htmlFor="email" className="text-white">
              Email
            </label>
            <input
              type="email"
              id="email"
              className="mt-2 p-2 w-full bg-gray-700 text-white rounded-lg"
              placeholder="Enter your email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div>
            <label htmlFor="password" className="text-white">
              Password
            </label>
            <input
              type="password"
              id="password"
              className="mt-2 p-2 w-full bg-gray-700 text-white rounded-lg"
              placeholder="Enter your password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <button
            type="submit"
            className="w-full bg-indigo-600 hover:bg-indigo-700 text-white font-semibold py-2 rounded-lg"
            disabled={loading}
          >
            {loading ? (
              <ClipLoader size={20} color={"#ffffff"} />
            ) : (
              "Sign in"
            )}
          </button>
        </form>
        <div className="mt-6 text-center text-white">
          <p>
            Don't have an account?{" "}
            <Link to="/register" className="text-indigo-400 hover:text-indigo-600">
              Sign up
            </Link>
          </p>
        </div>
      </div>
    </div>
   );
};

export default LoginForm;
