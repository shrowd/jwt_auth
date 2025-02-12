import React, { useEffect, useState } from "react";
import {useNavigate} from "react-router-dom";

import AuthService from "../services/authService";
import UserService from "../services/userService";

const UserPanel = () => {
  const [userInfo, setUserInfo] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUserInfo = async () => {
      const data = await UserService.getUserInfo();
      setUserInfo(data);
    };

    fetchUserInfo();
  }, []);

  return (
    <div className="min-h-screen bg-gray-900 flex items-center justify-center">
      <div className="bg-gray-800 p-8 rounded-lg shadow-lg w-full max-w-md text-center">
        {userInfo ? (
          <>
            <h2 className="text-2xl text-white font-bold mb-4">User panel</h2>
            <p className="text-lg text-gray-300 mb-6">{userInfo}</p>
            <button
              onClick={() => {
                AuthService.logout();
                navigate('/login');
              }}
              className="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded transition duration-300"
            >
              Sign out
            </button>
          </>
        ) : (
          <p className="text-white">Loading data ...</p>
        )}
      </div>
    </div>
  );
  };

export default UserPanel;
