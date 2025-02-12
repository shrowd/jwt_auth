import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

import ProtectedRoute from '../src/components/routes/ProtectedRoute'
import LoginForm from '../src/components/auth/LoginForm'; 
import RegisterForm from '../src/components/auth/RegisterForm';
import UserPanel from '../src/components/UserPanel';
import AdminPanel from '../src/components/AdminPanel';

const App = () => {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<LoginForm />} />
          <Route path="/login" element={<LoginForm />} />
          <Route path="/register" element={<RegisterForm />} />

          {/* PROTECTED ROUTES */}
          <Route element={<ProtectedRoute role="USER" />}>
          <Route path="/user" element={<UserPanel />} />
          </Route>
          <Route element={<ProtectedRoute role="ADMIN" />}>
          <Route path="/admin" element={<AdminPanel />} />
          </Route>

        </Routes>
      </div>
    </Router>
  );
};

export default App;
