import React from "react";
import axios from "axios";

function Groups() {
  axios.get(process.env.REACT_APP_API_URL + "/api/groups").then(
    (res) => {
      console.log(res.data);
    },
    (err) => {
      console.log(err.data);
    }
  );

  return (
    <>
      <div>Hello</div>
    </>
  );
}

export default Groups;
