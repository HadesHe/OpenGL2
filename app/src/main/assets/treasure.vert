attribute vec3 vPosition;
uniform mat4 uMVPMatrix;//变换矩阵
void main(){
    gl_Position=uMVPMatrix*vec4(vPosition, 1);
}