import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";

import Login from "./login/Login";
import RouteProtector from "./login/components/RouteProtector";
import AuthProvider from "./login/components/AuthProvider";
import DashboardLayout from "./common/components/DashboardLayout";
import Groups from "./common/components/Groups";
import axios from "axios";

function App() {
  axios.defaults.baseURL = process.env.REACT_APP_API_URL;

  return (
    <>
      <AuthProvider>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route
            path="/"
            element={
              <RouteProtector>
                <DashboardLayout />
              </RouteProtector>
            }
          >
            <Route path="groups" element={<Groups />} />
            <Route path="goods" element={<h1>Goods</h1>} />
          </Route>
          <Route path="*" element={<Navigate to="/goods" replace/>} />
        </Routes>
      </AuthProvider>
    </>
  );
}

export default App;
