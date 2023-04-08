import axios from "axios";
import { Fragment, useState } from "react";
import { Link } from "react-router-dom";
import "../../style/loginScreen.css";
import Cookies from "universal-cookie";
import img01 from "../../image/cyber.jpeg";
import { setAccessToken } from "../noticeboard/RefreshToken";
import img02 from "../../image/googleLoginButton.png";
import img03 from "../../image/kakaoLogInButton.png";

const Logins = () => {
  const cookies = new Cookies();
  const [idInfo, setInfo] = useState({
    uid: "",
    upw: "",
  });

  const headers = {
    "Content-Type": "application/json",
    "Access-Control-Allow-Origin": "*",
    "Access-Control-Allow-Credentials": true,
  };

  const kakaoLogin = () => {
    window.location
      .replace
      //  카카오 로그인 토큰
      ();
  };
  const googleLogin = () => {
    window.location
      .replace
      // 구글토큰 넣기
      ();
  };

  const jsonData = JSON.stringify(idInfo);
  const signUp = async () => {
    let flag = true;
    const signupResult = await axios({
      method: "POST",
      url: `/login`,
      headers: headers,
      data: jsonData,
    })
        .then((res) => {
          let jwtToken = res.headers.get("Authorization");
          localStorage.setItem("Authorization", jwtToken);
          axios.defaults.headers.common[
              "Authorization"
              ] = jwtToken;
          setAccessToken(jwtToken);
          cookies.set("myToken", idInfo.id, {
            path: "/",
            secure: true,
            maxAge: 3600,
          })
          window.location.replace("/noticeboard");
          return res.json();
        });
  }
  const changeIdPw = e => {
    const { name, value } = e.target;
    setInfo({ ...idInfo, [name]: value });
  };

  return (
    <Fragment>
      <div className="loginFrame">
        <Link to="/noticelist">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="26"
            height="26"
            fill="currentColor"
            className="bi bi-arrow-left Arrows"
            viewBox="0 0 16 16"
          >
            <path
              fillRule="evenodd"
              d="M15 8a.5.5 0 0 0-.5-.5H2.707l3.147-3.146a.5.5 0 1 0-.708-.708l-4 4a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L2.707 8.5H14.5A.5.5 0 0 0 15 8z"
            />
          </svg>
        </Link>
        <Link to="/register">
          <span className="registers">Register</span>
        </Link>
        <div className="loginArea">
          <form>
            <input
              type="text"
              className="idInput"
              placeholder="Username"
              name="uid"
              onChange={changeIdPw}
            />
            <input
              type="password"
              className="pwInput"
              placeholder="password"
              name="upw"
              onChange={changeIdPw}
            />
            <br />
            <Link to="/findid">
              <span className="findId">Forgot your Password?</span>
            </Link>
          </form>
          <div className="loginbuttonarea">
            <button type="submit" className="sbButton" onClick={signUp}>
              로그인
            </button>
          </div>
          <img
            src={img02}
            className="socialLoginButton"
            onClick={googleLogin}
            alt="img2"
          />
          <img
            src={img03}
            className="socialLoginButton"
            onClick={kakaoLogin}
            alt="img3"
          />
        </div>
      </div>
      <div className="platform">
        <span className="platformTextbox"></span>
        <span className="platformTextbox2"></span>
        <img src={img01} alt="img" className="loginImg" />
      </div>
    </Fragment>
  );

};



export default Logins;
