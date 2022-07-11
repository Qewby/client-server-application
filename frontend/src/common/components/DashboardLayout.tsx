import React from "react";
import { Outlet, useNavigate } from "react-router-dom";

import { AuthContext } from "../../login/components/AuthProvider";

function DashboardLayout() {
  const { user, signout } = React.useContext(AuthContext);
  const navigate = useNavigate();

  return (
    <>
      <main>
        <h2>Welcome to the dashboard page, {user?.login}!</h2>
        <button
          onClick={() => {
            signout(() => {
              navigate("/login");
            });
          }}
        >
          Log out
        </button>
        <Outlet />
      </main>
    </>
  );
}

export default DashboardLayout;
