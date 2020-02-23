uniform mat4 uMVPMatrix;
attribute vec3 aPosition;
varying vec3 vPosition;


uniform mat4 uMMatrix;
uniform vec3 uLightLocation;//光源位置
attribute vec3 aNormal;
varying vec4 vDiffuse;//散射光分量

//散射光光照计算的方法(法向量,散射光计算结果,光源位置,散射光强度)
void pointLight (in vec3 normal, inout vec4 diffuse, in vec3 lightLocation, in vec4 lightDiffuse){
    vec3 normalTarget=aPosition+normal;//计算变换后的法向量
    vec3 newNormal=(uMMatrix*vec4(normalTarget, 1)).xyz-(uMMatrix*vec4(aPosition, 1)).xyz;
    newNormal=normalize(newNormal);//对法向量规格化
    //计算从表面点到光源位置的向量vp
    vec3 vp= normalize(lightLocation-(uMMatrix*vec4(aPosition, 1)).xyz);
    vp=normalize(vp);//单位化vp
    float nDotViewPosition=max(0.0, dot(newNormal, vp));//求法向量与vp向量的点积与0的最大值
    diffuse=lightDiffuse*nDotViewPosition;//计算散射光的最终强度
}


void main() {
    gl_Position = uMVPMatrix*vec4(aPosition, 1);
    vPosition=aPosition;
    vec4 diffuseTemp=vec4(0.0, 0.0, 0.0, 0.0);
    pointLight(normalize(aNormal), diffuseTemp, uLightLocation, vec4(0.8, 0.8, 0.8, 1.0));
    vDiffuse=diffuseTemp;
}
